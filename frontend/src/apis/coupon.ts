import { client } from '@/apis';
import {
  AcceptedCouponListResponse,
  ChangeCouponStatusRequest,
  CouponDetailResponse,
  CreateCouponListResponse,
  CreateCouponRequest,
  ReceivedCouponListByStatusRequest,
  ReceivedCouponListByStatusResponse,
  ReceivedCouponListResponse,
  SentCouponListByStatusRequest,
  SentCouponListByStatusResponse,
  SentCouponListResponse,
} from '@/types/coupon/remote';

export const getCoupon = async (id: number) => {
  const { data } = await client.get<CouponDetailResponse>(`/coupons/${id}`);

  return data;
};

export const getAcceptedCouponList = async () => {
  const { data } = await client.get<AcceptedCouponListResponse>('/coupons/accept');

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

export const getSentCouponByStatusList = async ({ type }: SentCouponListByStatusRequest) => {
  const { data } = await client.get<SentCouponListByStatusResponse>(
    `/coupons/sent/status?type=${type}`
  );

  return data;
};

export const getReceivedCouponByStatusList = async ({
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
