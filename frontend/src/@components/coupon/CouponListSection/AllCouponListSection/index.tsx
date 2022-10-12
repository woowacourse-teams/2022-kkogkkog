import SmallCouponItem from '@/@components/coupon/CouponItem/small';
import HorizontalCouponList from '@/@components/coupon/CouponList/horizontal';
import { useFetchCouponListByStatus } from '@/@hooks/@queries/coupon';
import { Styled } from '@/@pages/coupon-list';
import { Coupon, COUPON_LIST_TYPE } from '@/types/coupon/client';

interface AllCouponListSectionProps {
  couponListType: COUPON_LIST_TYPE;
  onClickCouponItem: (coupon: Coupon) => void;
}

const AllCouponListSection = (props: AllCouponListSectionProps) => {
  const { couponListType, onClickCouponItem } = props;

  const { couponListByStatus: readyCouponList } = useFetchCouponListByStatus({
    couponListType,
    body: { type: 'READY' },
  });
  const { couponListByStatus: requestedCouponList } = useFetchCouponListByStatus({
    couponListType,
    body: { type: 'REQUESTED' },
  });
  const { couponListByStatus: acceptedCouponList } = useFetchCouponListByStatus({
    couponListType,
    body: { type: 'ACCEPTED' },
  });
  const { couponListByStatus: finishedCouponList } = useFetchCouponListByStatus({
    couponListType,
    body: { type: 'FINISHED' },
  });

  return (
    <Styled.HorizonListContainer>
      <section>
        <h2>기다리고 있어요!</h2>
        <HorizontalCouponList
          couponList={[...readyCouponList, ...requestedCouponList]}
          CouponItem={SmallCouponItem}
          onClickCouponItem={onClickCouponItem}
        />
      </section>
      <section>
        <h2>잡은 약속</h2>
        <HorizontalCouponList
          couponList={acceptedCouponList}
          CouponItem={SmallCouponItem}
          onClickCouponItem={onClickCouponItem}
        />
      </section>
      <section>
        <h2>지난 약속</h2>
        <HorizontalCouponList
          couponList={finishedCouponList}
          CouponItem={SmallCouponItem}
          onClickCouponItem={onClickCouponItem}
        />
      </section>
    </Styled.HorizonListContainer>
  );
};

export default AllCouponListSection;
