import Icon from '@/@components/@shared/Icon';
import Placeholder from '@/@components/@shared/Placeholder';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import ReservationList from '@/@components/reservation/ReservationList';
import theme from '@/styles/theme';
import { Reservation } from '@/types/coupon/client';
import { computeDay, generateDateKR, generateDday } from '@/utils/tobe-time';

import * as Styled from './style';

interface ReservationSectionProps {
  reservationList: Reservation[];
}

const ReservationSection = (props: ReservationSectionProps) => {
  const { reservationList } = props;

  if (reservationList.length === 0) {
    return (
      <Styled.NoneContentsContainer>
        <Icon iconName='hand' hasCursor={false} size='36' color={theme.colors.primary_400} />
        <h3>아직 예정된 약속이 없어요!</h3>
        <h4>약속을 기다리는 사람에게 신청해볼까요 ?</h4>
      </Styled.NoneContentsContainer>
    );
  }

  return (
    <Styled.Root>
      {reservationList.map(reservation => {
        const { meetingDate, coupons: reservatedCouponList } = reservation;

        const dateText = generateDateKR(meetingDate);
        const day = computeDay(meetingDate);
        const dDay = generateDday(meetingDate);

        return (
          <Styled.DateContainer key={meetingDate}>
            <Styled.DateTitle>
              <div>
                {dateText}({day})
              </div>
              <div>{dDay > 0 ? `D-${dDay}` : 'D-Day'}</div>
            </Styled.DateTitle>
            <ReservationList reservatedCouponList={reservatedCouponList} />
          </Styled.DateContainer>
        );
      })}
    </Styled.Root>
  );
};

export default ReservationSection;

ReservationSection.Skeleton = function Skeleton() {
  return (
    <Styled.Root>
      <Placeholder width='320px' height='320px'>
        <BigCouponItem.Skeleton />
        <BigCouponItem.Skeleton />
        <BigCouponItem.Skeleton />
      </Placeholder>
    </Styled.Root>
  );
};
