import axios from 'axios';

export const BASE_URL = process.env.PROD_SERVER;

export const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('user-token')}`,
  },
});
