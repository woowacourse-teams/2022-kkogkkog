import { useNavigate, useParams } from 'react-router-dom';

import PageTemplate from '@/@components/@shared/PageTemplate';
import UnregisteredCouponExpiredTime from '@/@components/unregistered-coupon/UnregisteredCouponExpiredTime';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import { useFetchUnregisteredCouponById } from '@/@hooks/@queries/unregistered-coupon';
import { useDeleteUnregisteredCoupon } from '@/@hooks/business/unregistered-coupon';
import NotFoundPage from '@/@pages/404';
import { PATH } from '@/Router';
import { unregisteredFilterOptionsSessionStorage } from '@/storage/session';

import * as Styled from './style';

const UnregisteredCouponDetail = () => {
  const navigate = useNavigate();

  const { unregisteredCouponId } = useParams();

  const { unregisteredCoupon } = useFetchUnregisteredCouponById(Number(unregisteredCouponId));

  const { deleteUnregisteredCoupon } = useDeleteUnregisteredCoupon(Number(unregisteredCouponId));

  if (!unregisteredCoupon) {
    return <NotFoundPage />;
  }

  const { couponMessage, createdTime } = unregisteredCoupon;

  const onClickDeleteButton = async () => {
    if (!window.confirm('쿠폰을 삭제하시겠습니까?')) {
      return;
    }

    await deleteUnregisteredCoupon();

    navigate(PATH.UNREGISTERED_COUPON_LIST, { replace: true });

    unregisteredFilterOptionsSessionStorage.set('미등록');
  };

  return (
    <PageTemplate.ExtendedStyleHeader title='미등록 쿠폰'>
      <Styled.Root>
        <Styled.Top>
          <UnregisteredCouponExpiredTime createdTime={createdTime} />
        </Styled.Top>
        <Styled.Main>
          <Styled.CouponInner>
            <UnregisteredCouponItem {...unregisteredCoupon} />
          </Styled.CouponInner>
          <Styled.SubSection>
            <Styled.SubSectionTitle>쿠폰 메시지</Styled.SubSectionTitle>
            <Styled.DescriptionContainer>{couponMessage}</Styled.DescriptionContainer>
          </Styled.SubSection>
          <Styled.FinishButtonInner>
            <button onClick={onClickDeleteButton}>쿠폰을 삭제하시겠습니까?</button>
          </Styled.FinishButtonInner>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate.ExtendedStyleHeader>
  );
};

export default UnregisteredCouponDetail;
