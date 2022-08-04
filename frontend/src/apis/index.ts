import axios from 'axios';

export const BASE_URL =
  process.env.NODE_ENV === 'production' ? process.env.PROD_SERVER : process.env.DEV_SERVER;

export const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('user-token')}`,
  },
});
