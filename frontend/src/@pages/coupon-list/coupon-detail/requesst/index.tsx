import { useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import useInput from '@/@hooks/@common/useInput';
import { useToast } from '@/@hooks/@common/useToast';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useFetchMe } from '@/@hooks/@queries/user';
import useChangeCouponStatus from '@/@hooks/coupon/useChangeCouponStatus';
import NotFoundPage from '@/@pages/404';
import { couponTypeTextMapper } from '@/constants/coupon';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { getToday, isBeforeToday } from '@/utils';
import { isOverMaxLength } from '@/utils/validations';

import * as Styled from './style';

const CouponRequestPage = () => {
  const navigate = useNavigate();
  const { couponId } = useParams();

  const [meetingDate, onChangeMeetingDate] = useInput('', [isBeforeToday]);
  const [message, onChangeMessage] = useInput('', [(value: string) => isOverMaxLength(value, 200)]);

  const { me } = useFetchMe();
  const { coupon } = useFetchCoupon(Number(couponId));

  const { requestCoupon } = useChangeCouponStatus(Number(couponId));

  const { displayMessage } = useToast();

  if (!coupon) {
    return <NotFoundPage />;
  }

  const { sender, receiver, couponType } = coupon;

  const isSent = me?.id === sender.id;

  const onClickRequestButton = () => {
    if (!meetingDate) {
      displayMessage('날짜를 지정해주세요.', true);

      return;
    }

    if (window.confirm('쿠폰 사용을 요청하시겠어요?')) {
      requestCoupon(
        { meetingDate },
        {
          onSuccessCallback() {
            navigate(PATH.LANDING);
          },
        }
      );
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
            <strong>
              {isSent ? `${receiver.nickname}님에게 ` : `${sender.nickname}님이 `}보낸
            </strong>
            &nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.SectionTitle>쿠폰을 사용하시겠어요? </Styled.SectionTitle>
          <Styled.Description>만남 날짜를 지정해보세요. (필수)</Styled.Description>
          <Styled.DateInput
            type='date'
            value={meetingDate}
            min={getToday()}
            data-placeholder='날짜 선택'
            onChange={onChangeMeetingDate}
            required
          />
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
            <Button onClick={onClickRequestButton} css={Styled.ExtendedButton}>
              사용 요청
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponRequestPage;
