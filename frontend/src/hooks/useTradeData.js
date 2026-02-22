import { useEffect, useMemo, useState } from 'react'
import GeoJSON from 'ol/format/GeoJSON'
import { Fill, Stroke, Style } from 'ol/style'
import { getPriceColor } from '../utils/mapStyle'
import api, { fetchPeriods, fetchTradeMap } from '../utils/api'

export default function useTradeData(vectorSource) {
  const [period, setPeriod] = useState(null)
  const [loading, setLoading] = useState(false)

  const geoJsonFormat = useMemo(() => new GeoJSON(), [])

  useEffect(() => {
    let mounted = true
    const init = async () => {
      setLoading(true)
      try {
        const periodsRes = await fetchPeriods()
        const periods = periodsRes.data?.periods ?? []
        if (periods.length === 0) return

        const latest = periods[0]
        if (!mounted) return
        setPeriod(latest)

        const mapRes = await fetchTradeMap(latest.year, latest.month)
        const featureCollection = mapRes.data
        const features = geoJsonFormat.readFeatures(featureCollection, {
          featureProjection: 'EPSG:3857',
        })

        features.forEach((feature) => {
          const avgPrice = Number(feature.get('avgPrice')) || 0
          feature.setStyle(
            new Style({
              fill: new Fill({ color: getPriceColor(avgPrice) }),
              stroke: new Stroke({ color: '#333', width: 1 }),
            })
          )
        })

        vectorSource.current.clear()
        vectorSource.current.addFeatures(features)
      } finally {
        if (mounted) setLoading(false)
      }
    }

    if (vectorSource?.current) {
      init()
    }

    return () => {
      mounted = false
    }
  }, [vectorSource, geoJsonFormat])

  return { period, loading }
}
