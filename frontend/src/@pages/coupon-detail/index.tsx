import { useNavigate, useParams } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import CouponHistoryList from '@/@components/coupon/CouponHistoryList';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useFetchMe } from '@/@hooks/@queries/user';
import { couponTypeTextMapper } from '@/constants/coupon';
import theme from '@/styles/theme';

import NotFoundPage from '../404';
import * as Styled from './style';

const CouponDetail = () => {
  const { couponId } = useParams();

  const { coupon } = useFetchCoupon(Number(couponId));
  const { me } = useFetchMe();

  const navigate = useNavigate();

  // Suspense 처리 후 NotFound 띄우기
  if (!coupon) {
    return <NotFoundPage />;
  }

  const { id, sender, receiver, couponType, couponHistories } = coupon;

  const isSent = me?.id === sender.id;

  return (
    <PageTemplate title='쿠폰' hasHeader={false}>
      <Styled.Top>
        <Icon
          iconName='arrow'
          size='20'
          color={theme.colors.primary_400}
          onClick={() => navigate(-1)}
        />
        <span>
          {isSent ? `${receiver.nickname}님에게 ` : `${sender.nickname}님이 `}보낸
          {couponTypeTextMapper[couponType]} 쿠폰
        </span>
        <Styled.CouponSummary />
      </Styled.Top>
      <BigCouponItem {...coupon} />
      <section>
        <span>소통 히스토리</span>
        <CouponHistoryList historyList={couponHistories} />
      </section>
      <div>
        <button>혹시 쿠폰을 사용하셨나요?</button>
        <button />
      </div>
    </PageTemplate>
  );
};

export default CouponDetail;
