import { useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import { useFetchMe } from '@/@hooks/@queries/user';
import { useRegisteredUnregisteredCoupon } from '@/@hooks/business/unregistered-coupon';
import { couponTypeTextMapper } from '@/constants/coupon';
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

  const { sender, couponMessage, couponType } = unregisteredCoupon;

  const navigate = useNavigate();

  const { me } = useFetchMe();

  const { registerUnregisteredCoupon } = useRegisteredUnregisteredCoupon({ couponCode });

  const onClickRegisterButton = () => {
    if (me) {
      registerUnregisteredCoupon({ couponCode });
      navigate(PATH.MAIN);
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
          <Styled.ProfileImage src={sender.imageUrl} alt='프로필' width={51} height={51} />
          <Styled.SummaryMessage>
            <strong>
              {sender.nickname}
              {'님이'} 보낸
            </strong>
            &nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
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
