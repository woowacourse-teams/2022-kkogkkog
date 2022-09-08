import axios from 'axios';

export const BASE_URL =
  PRODUCT_ENV === 'production'
    ? process.env.PROD_SERVER
    : process.env.DEV_SERVER ?? 'http://localhost:8080';

export const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('user-token')}`,
  },
});
