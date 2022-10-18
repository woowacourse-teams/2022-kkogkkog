import { useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import UnregisteredCouponExpiredTime from '@/@components/unregistered-coupon/UnregisteredCouponExpiredTime';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import { useToast } from '@/@hooks/@common/useToast';
import { useFetchMe } from '@/@hooks/@queries/user';
import { useRegisterUnregisteredCoupon } from '@/@hooks/business/unregistered-coupon';
import { PATH } from '@/Router';
import { unregisteredCouponCodeStorage } from '@/storage/session';
import { UnregisteredCouponResponse } from '@/types/unregistered-coupon/remote';

import * as Styled from './style';

interface IssuedUnregisteredCouponPageProps {
  unregisteredCoupon: UnregisteredCouponResponse;
  couponCode: string;
}

const IssuedUnregisteredCouponPage = (props: IssuedUnregisteredCouponPageProps) => {
  const { unregisteredCoupon, couponCode } = props;

  const { couponMessage, createdTime, sender } = unregisteredCoupon;

  const navigate = useNavigate();

  const { displayMessage } = useToast();
  const { me } = useFetchMe();

  const { registerUnregisteredCoupon } = useRegisterUnregisteredCoupon();

  const onClickRegisterButton = async () => {
    if (sender.id === me?.id) {
      displayMessage(`이 쿠폰은 ${me.nickname}님이 발급한 쿠폰이에요!`, true);

      return;
    }

    if (me) {
      await registerUnregisteredCoupon({ couponCode });

      navigate(PATH.MAIN);

      return;
    }

    if (window.confirm('쿠폰을 등록하려면 로그인이 필요합니다. 로그인하시겠아요?')) {
      navigate(PATH.LOGIN);
      unregisteredCouponCodeStorage.set(couponCode);
    }
  };

  return (
    <PageTemplate title='쿠폰' hasHeader={false}>
      <Styled.Root>
        <Styled.Top>
          <UnregisteredCouponExpiredTime createdTime={createdTime} />
        </Styled.Top>
        <Styled.Main>
          <Styled.CouponInner>
            <UnregisteredCouponItem.Preview {...unregisteredCoupon} />
          </Styled.CouponInner>
          <Styled.SubSection>
            <Styled.SubSectionTitle>쿠폰 메시지</Styled.SubSectionTitle>
            <Styled.DescriptionContainer>{couponMessage}</Styled.DescriptionContainer>
          </Styled.SubSection>

          <Position position='fixed' bottom='0' css={Styled.ExtendedPosition}>
            <Button onClick={onClickRegisterButton} css={Styled.ExtendedButton}>
              쿠폰 등록하기
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default IssuedUnregisteredCouponPage;
