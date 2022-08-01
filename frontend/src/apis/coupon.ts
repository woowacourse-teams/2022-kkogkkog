import { client } from '@/apis';
import { ChangeCouponStatusRequest, CreateCouponRequest } from '@/types/remote/request';
import { CouponListResponse, CouponResponse } from '@/types/remote/response';
//@TODO transformer 객체 만들기

export const getCoupon = (id: number) => client.get<CouponResponse>(`/coupons/${id}`);

export const getCouponList = () => client.get<CouponListResponse>('/coupons');

export const createCoupon = (info: CreateCouponRequest) => client.post('/coupons', info);

export const changeCouponStatus = ({ id, body }: { id: number; body: ChangeCouponStatusRequest }) =>
  client.post(`/coupons/${id}/event`, body);
