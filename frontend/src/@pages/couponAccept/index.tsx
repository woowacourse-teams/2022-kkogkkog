import { css } from '@emotion/react';
import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import useInput from '@/@hooks/@common/useInput';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useFetchMe } from '@/@hooks/@queries/user';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import NotFoundPage from '@/@pages/404';
import { couponTypeTextMapper } from '@/constants/coupon';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { generateDateText } from '@/utils';
import { isOverMaxLength } from '@/utils/validations';

import * as Styled from '../couponRequest/style';

const CouponAcceptPage = () => {
  const navigate = useNavigate();
  const { couponId } = useParams();

  const [message, onChangeMessage] = useInput('', [(value: string) => isOverMaxLength(value, 200)]);

  const { me } = useFetchMe();
  const { coupon } = useFetchCoupon(Number(couponId));

  const { acceptCoupon } = useChangeCouponStatus(Number(couponId));

  if (!coupon) {
    return <NotFoundPage />;
  }

  const { sender, receiver, couponType, meetingDate } = coupon;

  const isSent = me?.id === sender.id;

  const onClickAcceptButton = () => {
    if (window.confirm('쿠폰 사용 요청을 승인하시겠어요?')) {
      acceptCoupon({
        onSuccessCallback() {
          navigate(PATH.LANDING);
        },
      });
    }
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
          <Styled.ProfileImage src={isSent ? receiver.imageUrl : sender.imageUrl} alt='' />
          <Styled.SummaryMessage>
            {isSent ? `${receiver.nickname}님에게 ` : `${sender.nickname}님이 `}보낸&nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.SectionTitle>
            {generateDateText(meetingDate)}로 약속을 확정하시겠어요?
          </Styled.SectionTitle>
          <Styled.Description>메시지를 작성해주세요.</Styled.Description>
          <Position position='relative'>
            <Styled.MessageTextarea
              placeholder='시간, 장소 등 원하는 메시지를 보내보세요!'
              value={message}
              onChange={onChangeMessage}
            />
            <Position position='absolute' bottom='12px' right='12px'>
              <span>{message.length} / 200</span>
            </Position>
          </Position>
          <Position
            position='fixed'
            bottom='0'
            right='0'
            css={theme => css`
              width: 100%;
              display: flex;

              & > button + button {
                border-left: 1px solid ${theme.colors.grey_100};
              }
            `}
          >
            <Button onClick={onClickAcceptButton} css={Styled.ExtendedButton}>
              사용 승인
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponAcceptPage;
