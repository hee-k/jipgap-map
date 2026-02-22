import '../styles/map.css'

export default function PeriodFilter({ periods, value, onChange }) {
  const years = [...new Set(periods.map((p) => p.year))]
  const months = value
    ? periods.filter((p) => p.year === value.year).map((p) => p.month)
    : []

  return (
    <div className="period-filter">
      <select
        value={value?.year ?? ''}
        onChange={(e) => {
          const year = Number(e.target.value)
          const month = periods.find((p) => p.year === year)?.month
          onChange(month ? { year, month } : null)
        }}
      >
        <option value="" disabled>
          연도
        </option>
        {years.map((year) => (
          <option key={year} value={year}>
            {year}년
          </option>
        ))}
      </select>
      <select
        value={value?.month ?? ''}
        onChange={(e) => {
          const month = Number(e.target.value)
          onChange(value ? { year: value.year, month } : null)
        }}
      >
        <option value="" disabled>
          월
        </option>
        {months.map((month) => (
          <option key={month} value={month}>
            {month}월
          </option>
        ))}
      </select>
    </div>
  )
}
