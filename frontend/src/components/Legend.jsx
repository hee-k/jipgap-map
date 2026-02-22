import '../styles/map.css'
import { getPriceColor } from '../utils/mapStyle'

const legendItems = [
  { label: '~ 1억', value: 10000 },
  { label: '1억 ~ 2억', value: 20000 },
  { label: '2억 ~ 3억', value: 30000 },
  { label: '3억 ~ 5억', value: 50000 },
  { label: '5억 ~ 7억', value: 70000 },
  { label: '7억 ~ 10억', value: 100000 },
  { label: '10억 ~', value: 100001 },
]

export default function Legend() {
  return (
    <div className="legend">
      <div className="legend-title">평균 매매가(만원)</div>
      {legendItems.map((item) => (
        <div className="legend-item" key={item.label}>
          <span
            className="legend-color"
            style={{ backgroundColor: getPriceColor(item.value) }}
          />
          <span>{item.label}</span>
        </div>
      ))}
    </div>
  )
}
