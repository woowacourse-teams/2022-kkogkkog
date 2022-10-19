import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
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

  const { deleteUnregisteredCoupon } = useDeleteUnregisteredCoupon();

  if (!unregisteredCoupon) {
    return <NotFoundPage />;
  }

  const { couponMessage, createdTime } = unregisteredCoupon;

  const onClickDeleteButton = async () => {
    if (!window.confirm('쿠폰을 삭제하시겠습니까?')) {
      return;
    }

    const unregisteredCouponIdAsNumber = Number(unregisteredCouponId);

    await deleteUnregisteredCoupon(unregisteredCouponIdAsNumber);

    navigate(PATH.UNREGISTERED_COUPON_LIST);

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
        </Styled.Main>

        <Position position='fixed' bottom='0' right='0' css={Styled.ExtendedPosition}>
          <Button onClick={onClickDeleteButton} css={Styled.ExtendedButton}>
            쿠폰 삭제하기
          </Button>
        </Position>
      </Styled.Root>
    </PageTemplate.ExtendedStyleHeader>
  );
};

export default UnregisteredCouponDetail;
