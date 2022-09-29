import { client } from '@/apis';
import {
  ChangeCouponStatusRequest,
  CouponDetailResponse,
  CreateCouponListResponse,
  CreateCouponRequest,
  ReceivedCouponListByStatusRequest,
  ReceivedCouponListByStatusResponse,
  ReceivedCouponListResponse,
  ReservationListResponse,
  SentCouponListByStatusRequest,
  SentCouponListByStatusResponse,
  SentCouponListResponse,
} from '@/types/coupon/remote';

export const getCoupon = async (id: number) => {
  const { data } = await client.get<CouponDetailResponse>(`/coupons/${id}`);

  return data;
};

export const getReservationList = async () => {
  const { data } = await client.get<ReservationListResponse>('/coupons/accept');

  return data;
};

export const getSentCouponList = async () => {
  const { data } = await client.get<SentCouponListResponse>('/coupons/sent');

  return data;
};

export const getReceivedCouponList = async () => {
  const { data } = await client.get<ReceivedCouponListResponse>('/coupons/received');

  return data;
};

export const getSentCouponListByStatus = async ({ type }: SentCouponListByStatusRequest) => {
  const { data } = await client.get<SentCouponListByStatusResponse>(
    `/coupons/sent/status?type=${type}`
  );

  return data;
};

export const getReceivedCouponListByStatus = async ({
  type,
}: ReceivedCouponListByStatusRequest) => {
  const { data } = await client.get<ReceivedCouponListByStatusResponse>(
    `/coupons/received/status?type=${type}`
  );

  return data;
};

export const createCoupon = (body: CreateCouponRequest) =>
  client.post<CreateCouponListResponse>('/coupons', body);

export const changeCouponStatus = ({
  couponId,
  body,
}: {
  couponId: number;
  body: ChangeCouponStatusRequest;
}) => client.put(`/coupon/${couponId}/event`, body);
