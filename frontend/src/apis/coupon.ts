import { client } from '@/apis';
import { ChangeCouponStatusRequest, CreateCouponRequest } from '@/types/remote/request';
import { CouponListResponse, CouponResponse } from '@/types/remote/response';

export const getCoupon = (id: number) => client.get<CouponResponse>(`/coupons/${id}`);

export const getCouponList = () => client.get<CouponListResponse>('/coupons');

export const createCoupon = (info: CreateCouponRequest) => client.post('/coupons', info);

export const changeCouponStatus = ({ id, body }: { id: number; body: ChangeCouponStatusRequest }) =>
  client.post(`/coupons/${id}/event`, body);

export const requestCoupon = ({ id, body }: { id: number; body: ChangeCouponStatusRequest }) =>
  client.post(`/coupons/${id}/event/request`, body);
