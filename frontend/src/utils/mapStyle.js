export function getPriceColor(avgPrice) {
  if (avgPrice <= 10000) return '#EFF3FF'
  if (avgPrice <= 20000) return '#C6DBEF'
  if (avgPrice <= 30000) return '#9ECAE1'
  if (avgPrice <= 50000) return '#FEE08B'
  if (avgPrice <= 70000) return '#FDAE61'
  if (avgPrice <= 100000) return '#F46D43'
  return '#A50026'
}
