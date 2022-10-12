import { useNavigate } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import UnregisteredCouponItem from '@/@components/unregistered-coupon/UnregisteredCouponItem';
import { useRegisteredUnregisteredCoupon } from '@/@hooks/business/unregistered-coupon';
import { couponTypeTextMapper } from '@/constants/coupon';
import theme from '@/styles/theme';
import { UnregisteredCouponResponse } from '@/types/unregistered-coupon/remote';

import * as Styled from './style';

interface IssuedRegisteredCouponPageProps {
  unregisteredCoupon: UnregisteredCouponResponse;
  couponCode: string;
}

const IssuedRegisteredCouponPage = (props: IssuedRegisteredCouponPageProps) => {
  const { unregisteredCoupon, couponCode } = props;

  const { sender, couponMessage, couponType } = unregisteredCoupon;

  const navigate = useNavigate();

  const { registerUnregisteredCoupon } = useRegisteredUnregisteredCoupon({ couponCode });

  const onClickRegisterButton = () => {
    registerUnregisteredCoupon({ couponCode });
  };

  return (
    <PageTemplate title='쿠폰' hasHeader={false}>
      <Styled.Root>
        <Styled.Top>
          <Position position='absolute' top='20px' left='20px'>
            <Icon
              iconName='arrow'
              size='20'
              color={theme.colors.primary_400}
              onClick={() => navigate(-1)}
            />
          </Position>
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

export default IssuedRegisteredCouponPage;
