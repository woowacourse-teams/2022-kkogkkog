import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import useInput from '@/@hooks/@common/useInput';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useFetchMe } from '@/@hooks/@queries/user';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import NotFoundPage from '@/@pages/404';
import { couponTypeTextMapper } from '@/constants/coupon';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { generateDateText } from '@/utils';
import { isOverMaxLength } from '@/utils/validations';

import * as Styled from '../request/style';

const CouponDeclinePage = () => {
  const navigate = useNavigate();
  const { couponId } = useParams();

  const [message, onChangeMessage] = useInput('', [(value: string) => isOverMaxLength(value, 200)]);

  const { me } = useFetchMe();
  const { coupon } = useFetchCoupon(Number(couponId));

  const { declineCoupon } = useChangeCouponStatus(Number(couponId));

  usePreventReload();

  if (!coupon) {
    return <NotFoundPage />;
  }

  const { senderId, senderNickname, receiverNickname, imageUrl, couponType, meetingDate } = coupon;

  const isSent = me?.id === senderId;

  const onClickDeclineButton = () => {
    if (window.confirm('쿠폰 사용 요청을 거절하시겠어요?')) {
      declineCoupon({
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
          <Styled.ProfileImage src={imageUrl} alt='' />
          <Styled.SummaryMessage>
            <strong>{isSent ? `${receiverNickname}님에게 ` : `${senderNickname}님이 `}보낸</strong>
            &nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.SectionTitle>
            {generateDateText(meetingDate)}에 만남이 어려우신가요?
          </Styled.SectionTitle>
          <Styled.Description>메시지를 작성해보세요. (선택)</Styled.Description>
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
          <Position position='fixed' bottom='0' right='0' css={Styled.ExtendedPosition}>
            <Button onClick={onClickDeclineButton} css={Styled.ExtendedButton}>
              사용 거절
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponDeclinePage;
