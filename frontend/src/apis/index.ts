import axios from 'axios';

export const BASE_URL = 'http://localhost:8080/api';

export const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('user-token')}`,
  },
});
