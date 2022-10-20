import { Navigate, useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import useInput from '@/@hooks/@common/useInput';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import { useToast } from '@/@hooks/@common/useToast';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useChangeCouponStatus } from '@/@hooks/business/coupon';
import useCouponPartner from '@/@hooks/ui/coupon/useCouponPartner';
import NotFoundPage from '@/@pages/404';
import { couponTypeTextMapper } from '@/constants/coupon';
import { YYYYMMDD } from '@/types/utils';
import { getTodayDate, isBeforeToday } from '@/utils/time';
import { isOverMaxLength } from '@/utils/validations';

import * as Styled from './style';

const CouponRequestPage = () => {
  usePreventReload();

  const navigate = useNavigate();
  const { couponId } = useParams();

  const todayDate = getTodayDate();

  const [meetingDate, onChangeMeetingDate] = useInput<YYYYMMDD>(todayDate, [isBeforeToday]);

  const [meetingMessage, onChangeMeetingMessage] = useInput('', [
    (value: string) => isOverMaxLength(value, 200),
  ]);

  const { coupon } = useFetchCoupon(Number(couponId));

  const { requestCoupon } = useChangeCouponStatus({
    couponId: Number(couponId),
  });

  const { displayMessage } = useToast();

  const { isSent, member } = useCouponPartner(coupon);

  if (!coupon || !member) {
    return <NotFoundPage />;
  }

  if (coupon.couponStatus !== 'READY') {
    return <Navigate to={-1} />;
  }

  if (isSent) {
    return <Navigate to={-1} />;
  }

  const { couponType } = coupon;

  const onClickRequestButton = async () => {
    if (!meetingDate) {
      displayMessage('날짜를 지정해주세요.', true);

      return;
    }

    if (!window.confirm('쿠폰 사용을 요청하시겠어요?')) {
      return;
    }

    await requestCoupon({ meetingMessage, meetingDate });

    navigate(-1);
  };

  return (
    <PageTemplate.ExtendedStyleHeader title='쿠폰 요청하기'>
      <Styled.Root>
        <Styled.Top>
          <Styled.ProfileImage src={member.imageUrl} alt='프로필' width={51} height={51} />
          <Styled.SummaryMessage>
            <strong>
              {member.nickname} {isSent ? '님에게' : '님이'} 보낸
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
            min={todayDate}
            data-placeholder='날짜 선택'
            onChange={onChangeMeetingDate}
            required
          />
          <Styled.Description>메시지를 작성해보세요. (선택)</Styled.Description>

          <Styled.TextareaContainer>
            <Styled.MessageTextareaContainer>
              <Styled.MessageTextarea
                id='message-textarea'
                placeholder='시간, 장소 등 원하는 메시지를 보내보세요!'
                value={meetingMessage}
                onChange={onChangeMeetingMessage}
              />
              <Styled.MessageLength>{meetingMessage.length} / 200</Styled.MessageLength>
            </Styled.MessageTextareaContainer>
          </Styled.TextareaContainer>

          <Position position='fixed' bottom='0' right='0' css={Styled.ExtendedPosition}>
            <Button onClick={onClickRequestButton} css={Styled.ExtendedButton}>
              약속 요청하기
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate.ExtendedStyleHeader>
  );
};

export default CouponRequestPage;
