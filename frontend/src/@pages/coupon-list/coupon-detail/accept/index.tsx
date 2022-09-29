import { Navigate, useNavigate, useParams } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import Icon from '@/@components/@shared/Icon';
import PageTemplate from '@/@components/@shared/PageTemplate';
import Position from '@/@components/@shared/Position';
import useInput from '@/@hooks/@common/useInput';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import { useFetchCoupon } from '@/@hooks/@queries/coupon';
import { useChangeCouponStatus } from '@/@hooks/business/coupon';
import useCouponPartner from '@/@hooks/ui/coupon/useCouponPartner';
import NotFoundPage from '@/@pages/404';
import { couponTypeTextMapper } from '@/constants/coupon';
import { PATH } from '@/Router';
import theme from '@/styles/theme';
import { generateDateText } from '@/utils/time';
import { isOverMaxLength } from '@/utils/validations';

import * as Styled from '../request/style';

const CouponAcceptPage = () => {
  usePreventReload();

  const navigate = useNavigate();
  const { couponId } = useParams();

  const [meetingMessage, onChangeMeetingMessage] = useInput('', [
    (value: string) => isOverMaxLength(value, 200),
  ]);
  const { coupon } = useFetchCoupon(Number(couponId));

  const { acceptCoupon } = useChangeCouponStatus({
    couponId: Number(couponId),
  });

  const { isSent, member } = useCouponPartner(coupon);

  if (!coupon || !member) {
    return <NotFoundPage />;
  }

  if (coupon.couponStatus !== 'REQUESTED') {
    return <Navigate to={-1} />;
  }

  if (!isSent) {
    return <Navigate to={-1} />;
  }

  const { couponType, meetingDate } = coupon;

  const onClickAcceptButton = async () => {
    if (!window.confirm('쿠폰 사용 요청을 승인하시겠어요?')) {
      return;
    }

    await acceptCoupon({ meetingMessage });

    if (isSent) {
      navigate(PATH.SENT_COUPON_LIST, { replace: true });
    } else {
      navigate(PATH.RECEIVED_COUPON_LIST, { replace: true });
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
          <Styled.ProfileImage src={member?.imageUrl} alt='프로필' width={51} height={51} />
          <Styled.SummaryMessage>
            <strong>
              {member.nickname} {isSent ? '님에게' : '님이'} 보낸
            </strong>
            &nbsp;
            {couponTypeTextMapper[couponType]} 쿠폰
          </Styled.SummaryMessage>
        </Styled.Top>
        <Styled.Main>
          <Styled.SectionTitle>
            {generateDateText(meetingDate)}로 약속을 확정하시겠어요?
          </Styled.SectionTitle>

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
            <Button onClick={onClickAcceptButton} css={Styled.ExtendedButton}>
              약속 확정하기
            </Button>
          </Position>
        </Styled.Main>
      </Styled.Root>
    </PageTemplate>
  );
};

export default CouponAcceptPage;
