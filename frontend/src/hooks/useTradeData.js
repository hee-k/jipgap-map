import { useEffect, useMemo, useState } from 'react'
import GeoJSON from 'ol/format/GeoJSON'
import { Fill, Stroke, Style } from 'ol/style'
import { getPriceColor } from '../utils/mapStyle'
import { fetchPeriods, fetchTradeMap } from '../utils/api'

export default function useTradeData(vectorSource, period) {
  const [periods, setPeriods] = useState([])
  const [loading, setLoading] = useState(false)

  const geoJsonFormat = useMemo(() => new GeoJSON(), [])

  useEffect(() => {
    let mounted = true
    const loadPeriods = async () => {
      const periodsRes = await fetchPeriods()
      const nextPeriods = periodsRes.data?.periods ?? []
      if (mounted) setPeriods(nextPeriods)
    }
    loadPeriods()
    return () => {
      mounted = false
    }
  }, [])

  useEffect(() => {
    let mounted = true
    const init = async () => {
      if (!period) return
      setLoading(true)
      try {
        const mapRes = await fetchTradeMap(period.year, period.month)
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
  }, [vectorSource, geoJsonFormat, period])

  return { periods, loading }
}
