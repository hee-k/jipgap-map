import { useEffect } from 'react'
import MapContainer from './components/MapContainer'
import Legend from './components/Legend'
import useMap from './hooks/useMap'
import useTradeData from './hooks/useTradeData'
import './styles/map.css'

function App() {
  const { vectorSource } = useMap('map')
  const { period, loading } = useTradeData(vectorSource)

  return (
    <div style={{ position: 'relative' }}>
      <MapContainer />
      <Legend />
      <div style={{ position: 'absolute', top: 16, left: 16, background: 'rgba(255,255,255,0.9)', padding: '8px 12px', borderRadius: 6, fontSize: 12 }}>
        {loading ? 'Loading…' : period ? `${period.year}년 ${period.month}월` : '데이터 없음'}
      </div>
    </div>
  )
}

export default App
