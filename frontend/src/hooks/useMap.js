import { useEffect, useRef } from 'react'
import Map from 'ol/Map'
import View from 'ol/View'
import TileLayer from 'ol/layer/Tile'
import VectorLayer from 'ol/layer/Vector'
import OSM from 'ol/source/OSM'
import VectorSource from 'ol/source/Vector'
import { fromLonLat } from 'ol/proj'

export default function useMap(targetId) {
  const mapRef = useRef(null)
  const vectorSourceRef = useRef(new VectorSource())
  const vectorLayerRef = useRef(new VectorLayer({ source: vectorSourceRef.current }))

  useEffect(() => {
    if (mapRef.current) return

    const map = new Map({
      target: targetId,
      layers: [
        new TileLayer({ source: new OSM() }),
        vectorLayerRef.current,
      ],
      view: new View({
        center: fromLonLat([126.978, 37.566]),
        zoom: 11,
      }),
    })

    mapRef.current = map
    return () => map.setTarget(undefined)
  }, [targetId])

  return { map: mapRef, vectorSource: vectorSourceRef, vectorLayer: vectorLayerRef }
}
