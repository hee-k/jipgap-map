import '../styles/map.css'

export default function Popup({ detail, onClose }) {
  if (!detail) return null

  return (
    <div className="popup">
      <div className="popup-header">
        <div>
          <div className="popup-title">{detail.sggName ?? detail.sggCd}</div>
          <div className="popup-subtitle">
            {detail.year}년 {detail.month}월
          </div>
        </div>
        <button className="popup-close" onClick={onClose}>
          ×
        </button>
      </div>

      <div className="popup-summary">
        <div>평균가: {detail.summary?.avgPrice?.toLocaleString?.() ?? '-'} 만원</div>
        <div>거래건수: {detail.summary?.tradeCount?.toLocaleString?.() ?? '-'} 건</div>
      </div>

      <div className="popup-list">
        {detail.topApts?.slice(0, 5).map((apt) => (
          <div key={apt.aptName} className="popup-item">
            <span>{apt.aptName}</span>
            <span>{apt.avgPrice?.toLocaleString?.() ?? '-'} 만원</span>
          </div>
        ))}
      </div>
    </div>
  )
}
