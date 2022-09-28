import { useQueryClient } from 'react-query';

import { useLoading } from '@/@hooks/@common/useLoading';
import {
  changeCouponStatus,
  createCoupon,
  getAcceptedCouponList,
  getCoupon,
  getReceivedCouponList,
  getReceivedCouponListByStatus,
  getSentCouponList,
  getSentCouponListByStatus,
} from '@/apis/coupon';
import {
  ReceivedCouponListByStatusRequest,
  SentCouponListByStatusRequest,
} from '@/types/coupon/remote';

import { useMutation, useQuery } from './utils';

/** Query */

// export const useFetchCouponList = () => {
//   /** suspense false만 isLoading을 사용할 수 있다. */
//   const { data, ...rest } = useQuery([QUERY_KEY.couponList], getCouponList, {
//     suspense: true,
//     staleTime: 10000,
//   });

//   const couponList = useMemo(() => data?.data ?? { received: [], sent: [] }, [data?.data]);

//   const parseCouponList = (couponStatus: COUPON_LIST_TYPE) => {
//     return couponList[couponStatus].reduceRight<Record<COUPON_STATUS, CouponResponse[]>>(
//       (prev, coupon) => {
//         const key = coupon.couponStatus;

//         return { ...prev, [key]: [...prev[key], coupon] };
//       },
//       {
//         REQUESTED: [],
//         READY: [],
//         ACCEPTED: [],
//         FINISHED: [],
//       }
//     );
//   };

//   const parseOpenCouponList = (couponStatus: COUPON_LIST_TYPE) => {
//     const parsedCouponList = parseCouponList(couponStatus);

//     return [...parsedCouponList.REQUESTED, ...parsedCouponList.READY];
//   };

//   const generateReservationRecord = () => {
//     const combinedCouponList = [...couponList.received, ...couponList.sent];

//     return combinedCouponList.reduce<Record<string, CouponResponse[]>>((prev, coupon) => {
//       const { couponStatus, meetingDate } = coupon;

//       if (couponStatus === 'ACCEPTED' && meetingDate) {
//         return { ...prev, [meetingDate]: [...(prev[meetingDate] ?? []), coupon] };
//       }

//       return prev;
//     }, {});
//   };

//   return {
//     couponList,
//     isLoading: rest.isLoading,
//     parseCouponList,
//     parseOpenCouponList,
//     generateReservationRecord,
//   };
// };

const QUERY_KEY = {
  coupon: 'coupon',
  acceptedCouponList: 'acceptedCouponList',
  sentCouponList: 'sentCouponList',
  receivedCouponList: 'receivedCouponList',
  sentCouponListByStatus: 'sentCouponListByStatus',
  receivedCouponListByStatus: 'receivedCouponListByStatus',
};

export const useFetchCoupon = (id: number) => {
  const { data } = useQuery([QUERY_KEY.coupon, id], () => getCoupon(id));

  return {
    coupon: data,
  };
};

export const useFetchAcceptedCouponList = () => {
  const { data, isLoading } = useQuery([QUERY_KEY.acceptedCouponList], () =>
    getAcceptedCouponList()
  );

  return {
    acceptedCouponList: data,
    isLoading,
  };
};

export const useFetchSentCouponList = () => {
  const { data, isLoading } = useQuery([QUERY_KEY.sentCouponList], () => getSentCouponList());

  return {
    sentCouponList: data,
    isLoading,
  };
};

export const useFetchReceivedCouponList = () => {
  const { data, isLoading } = useQuery([QUERY_KEY.receivedCouponList], () =>
    getReceivedCouponList()
  );

  return {
    receivedCouponList: data,
    isLoading,
  };
};

export const useFetchSentCouponListByStatus = (body: SentCouponListByStatusRequest) => {
  const { data, isLoading } = useQuery([QUERY_KEY.sentCouponListByStatus], () =>
    getSentCouponListByStatus(body)
  );

  return {
    acceptedCouponList: data,
    isLoading,
  };
};

export const useFetchReceivedCouponListByStatus = (body: ReceivedCouponListByStatusRequest) => {
  const { data, isLoading } = useQuery([QUERY_KEY.receivedCouponListByStatus], () =>
    getReceivedCouponListByStatus(body)
  );

  return {
    acceptedCouponList: data,
    isLoading,
  };
};

/** Mutation */

export const useCreateCouponMutation = () => {
  const queryClient = useQueryClient();
  const { showLoading, hideLoading } = useLoading();

  return useMutation(createCoupon, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.sentCouponList);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};

export const useChangeCouponStatusMutation = (id: number) => {
  const queryClient = useQueryClient();
  const { showLoading, hideLoading } = useLoading();

  return useMutation(changeCouponStatus, {
    onSuccess() {
      // queryClient.invalidateQueries(QUERY_KEY.couponList);
      // invalidate 해주기 애매하다. 쿠폰은 staleTime을 0으로 두고 항상 refetch하도록 두자.
      queryClient.invalidateQueries([QUERY_KEY.coupon, id]);
    },
    onMutate() {
      showLoading();
    },
    onSettled() {
      hideLoading();
    },
  });
};
