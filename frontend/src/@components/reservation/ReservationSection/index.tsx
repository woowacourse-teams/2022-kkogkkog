import Icon from '@/@components/@shared/Icon';
import Placeholder from '@/@components/@shared/Placeholder';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import ReservationList from '@/@components/reservation/ReservationList';
import theme from '@/styles/theme';
import { Reservation } from '@/types/coupon/client';
import { computeDay, generateDateText, generateDDay } from '@/utils/time';

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

        const dateText = generateDateText(meetingDate);
        const day = computeDay(meetingDate);
        const dDay = generateDDay(meetingDate);

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

// const scrollContainer = useRef<HTMLDivElement>(null);

// const onClickRightArrow = () => {
//   const { current: element } = scrollContainer;

//   if (element) {
//     const { clientWidth, scrollWidth, offsetWidth, scrollLeft, clientLeft } = element;

//     element?.scroll({
//       left: scrollLeft + offsetWidth,
//       behavior: 'smooth',
//     });
//   }
// };

// const onClickLeftArrow = () => {
//   const { current: element } = scrollContainer;

//   if (element) {
//     const { clientWidth, scrollWidth, offsetWidth, scrollLeft } = element;

//     element?.scroll({ left: scrollLeft - offsetWidth, behavior: 'smooth' });
//   }
// };
/* <Styled.LeftArrow onClick={onClickLeftArrow}>
          <Icon iconName='arrow' color={theme.colors.primary_400} size='36' />
        </Styled.LeftArrow>
        <Styled.RightArrow onClick={onClickRightArrow}>
          <Icon iconName='arrow' color={theme.colors.primary_400} size='36' />
        </Styled.RightArrow> */
