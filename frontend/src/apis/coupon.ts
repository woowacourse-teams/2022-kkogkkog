import { client } from '@/apis';
import {
  ChangeCouponStatusRequest,
  CouponReservationRequest,
  CreateCouponRequest,
} from '@/types/remote/request';
import {
  CouponCreateResponse,
  CouponDetailResponse,
  CouponListResponse,
} from '@/types/remote/response';

export const getCoupon = (id: number) => client.get<CouponDetailResponse>(`/coupons/${id}`);

export const getCouponList = () => client.get<CouponListResponse>('/coupons');

export const createCoupon = (info: CreateCouponRequest) =>
  client.post<CouponCreateResponse>('/coupons', info);

export const changeCouponStatus = ({
  reservationId,
  body,
}: {
  reservationId: number | null;
  body: ChangeCouponStatusRequest;
}) => client.put(`/reservations/${reservationId}`, body);

// 고차 함수로 만들어서 body만 따로 받는 것은 어떨까?
export const reserveCoupon = ({ body }: { body: CouponReservationRequest }) =>
  client.post('/reservations', body);
