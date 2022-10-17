import CustomSuspense from '@/@components/@shared/CustomSuspense';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import { useFetchCouponListByStatus } from '@/@hooks/@queries/coupon';
import { Styled } from '@/@pages/coupon-list';
import { Coupon, COUPON_LIST_TYPE } from '@/types/coupon/client';

interface AcceptedCouponListSectionProps {
  couponListType: COUPON_LIST_TYPE;
  onClickCouponItem: (coupon: Coupon) => void;
}

const AcceptedCouponListSection = (props: AcceptedCouponListSectionProps) => {
  const { couponListType, onClickCouponItem } = props;

  const { couponListByStatus: acceptedCouponList, isLoading: isAcceptedCouponListLoading } =
    useFetchCouponListByStatus({
      couponListType,
      body: { type: 'ACCEPTED' },
    });

  return (
    <Styled.VerticalListContainer>
      <CustomSuspense
        isLoading={isAcceptedCouponListLoading}
        fallback={<VerticalCouponList.Skeleton CouponItemSkeleton={BigCouponItem.Skeleton} />}
      >
        <VerticalCouponList
          couponList={acceptedCouponList}
          CouponItem={BigCouponItem}
          onClickCouponItem={onClickCouponItem}
        />
      </CustomSuspense>
    </Styled.VerticalListContainer>
  );
};

export default AcceptedCouponListSection;
