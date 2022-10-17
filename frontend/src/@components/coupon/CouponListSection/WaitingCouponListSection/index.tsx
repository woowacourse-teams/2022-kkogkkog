import CustomSuspense from '@/@components/@shared/CustomSuspense';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import { useFetchCouponListByStatus } from '@/@hooks/@queries/coupon';
import { Styled } from '@/@pages/coupon-list';
import { Coupon, COUPON_LIST_TYPE } from '@/types/coupon/client';

interface WaitingCouponListSectionProps {
  couponListType: COUPON_LIST_TYPE;
  onClickCouponItem: (coupon: Coupon) => void;
}

const WaitingCouponListSection = (props: WaitingCouponListSectionProps) => {
  const { couponListType, onClickCouponItem } = props;

  const { couponListByStatus: readyCouponList, isLoading: isReadyCouponListLoading } =
    useFetchCouponListByStatus({
      couponListType,
      body: { type: 'READY' },
    });
  const { couponListByStatus: requestedCouponList, isLoading: isRequestedCouponListLoading } =
    useFetchCouponListByStatus({
      couponListType,
      body: { type: 'REQUESTED' },
    });

  return (
    <Styled.VerticalListContainer>
      <CustomSuspense
        isLoading={isReadyCouponListLoading || isRequestedCouponListLoading}
        fallback={<VerticalCouponList.Skeleton CouponItemSkeleton={BigCouponItem.Skeleton} />}
      >
        <VerticalCouponList
          couponList={[...readyCouponList, ...requestedCouponList]}
          CouponItem={BigCouponItem}
          onClickCouponItem={onClickCouponItem}
        />
      </CustomSuspense>
    </Styled.VerticalListContainer>
  );
};

export default WaitingCouponListSection;
