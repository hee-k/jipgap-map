import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
})

export const fetchTradeMap = (year, month) =>
  api.get('/v1/trade/map', { params: { year, month } })

export const fetchTradeDetail = (sggCd, year, month) =>
  api.get('/v1/trade/detail', { params: { sggCd, year, month } })

export const fetchPeriods = () => api.get('/v1/trade/periods')

export default api
