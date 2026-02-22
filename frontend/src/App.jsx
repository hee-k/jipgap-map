import { useEffect, useRef, useState } from 'react'
import MapContainer from './components/MapContainer'
import Legend from './components/Legend'
import PeriodFilter from './components/PeriodFilter'
import Popup from './components/Popup'
import useMap from './hooks/useMap'
import useTradeData from './hooks/useTradeData'
import { fetchTradeDetail } from './utils/api'
import './styles/map.css'

function App() {
  const { map, vectorSource } = useMap('map')
  const [period, setPeriod] = useState(null)
  const { periods, loading } = useTradeData(vectorSource, period)
  const [detail, setDetail] = useState(null)
  const [hoverText, setHoverText] = useState('')
  const tooltipRef = useRef(null)

  useEffect(() => {
    if (!period && periods.length > 0) {
      setPeriod(periods[0])
    }
  }, [periods, period])

  useEffect(() => {
    const mapInstance = map.current
    if (!mapInstance) return

    const handlePointerMove = (evt) => {
      const feature = mapInstance.forEachFeatureAtPixel(evt.pixel, (f) => f)
      if (feature) {
        setHoverText(feature.get('sggKorNm'))
        if (tooltipRef.current) {
          tooltipRef.current.style.left = `${evt.pixel[0] + 12}px`
          tooltipRef.current.style.top = `${evt.pixel[1] + 12}px`
        }
      } else {
        setHoverText('')
      }
    }

    const handleClick = async (evt) => {
      const feature = mapInstance.forEachFeatureAtPixel(evt.pixel, (f) => f)
      if (!feature || !period) return
      const sggCd = feature.get('sggCd')
      const sggKorNm = feature.get('sggKorNm')
      const res = await fetchTradeDetail(sggCd, period.year, period.month)
      setDetail({ ...res.data, sggName: sggKorNm })
    }

    mapInstance.on('pointermove', handlePointerMove)
    mapInstance.on('click', handleClick)

    return () => {
      mapInstance.un('pointermove', handlePointerMove)
      mapInstance.un('click', handleClick)
    }
  }, [map, period])

  return (
    <div style={{ position: 'relative' }}>
      <MapContainer />
      <Legend />
      <PeriodFilter periods={periods} value={period} onChange={setPeriod} />
      <Popup detail={detail} onClose={() => setDetail(null)} />
      {hoverText && (
        <div ref={tooltipRef} className="tooltip">
          {hoverText}
        </div>
      )}
      <div
        style={{
          position: 'absolute',
          top: 16,
          left: 16,
          background: 'rgba(255,255,255,0.9)',
          padding: '8px 12px',
          borderRadius: 6,
          fontSize: 12,
        }}
      >
        {loading ? 'Loading…' : period ? `${period.year}년 ${period.month}월` : '데이터 없음'}
      </div>
    </div>
  )
}

export default App
