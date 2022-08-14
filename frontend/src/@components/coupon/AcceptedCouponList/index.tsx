import { useMemo } from 'react';
import { useNavigate } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import Placeholder from '@/@components/@shared/Placeholder';
import BigCouponItem from '@/@components/coupon/CouponItem/big';
import VerticalCouponList from '@/@components/coupon/CouponList/vertical';
import theme from '@/styles/theme';
import { CouponResponse } from '@/types/remote/response';
import { generateDateText } from '@/utils';

import * as Styled from './style';

interface AcceptedCouponListProps {
  acceptedCouponList: Record<string, CouponResponse[]>;
}

const AcceptedCouponList = (props: AcceptedCouponListProps) => {
  const { acceptedCouponList } = props;

  const navigate = useNavigate();

  const sortedKey = useMemo(
    () =>
      Object.keys(acceptedCouponList).sort((a, b) => {
        return Number(a.replace(/-/g, '')) - Number(b.replace(/-/g, ''));
      }),
    [acceptedCouponList]
  );

  const onClickCouponItem = (coupon: CouponResponse) => {
    navigate(`/coupon-list/${coupon.couponId}`);
  };

  if (Object.keys(acceptedCouponList).length === 0) {
    return (
      <Styled.NoneContentsContainer>
        <Icon iconName='hand' size='36' color={theme.colors.primary_400} />
        <h3>아직 승인된 약속이 존재하지 않아요!</h3>
        <h4>약속을 기다리는 사람에게 신청해볼까요 ?</h4>
      </Styled.NoneContentsContainer>
    );
  }

  return (
    <Styled.Root>
      {sortedKey.map(date => (
        <Styled.DateContainer key={date}>
          <Styled.DateTitle>{generateDateText(date)}</Styled.DateTitle>
          <VerticalCouponList
            CouponItem={BigCouponItem}
            couponList={acceptedCouponList[date]}
            onClickCouponItem={onClickCouponItem}
          />
        </Styled.DateContainer>
      ))}
    </Styled.Root>
  );
};

export default AcceptedCouponList;

AcceptedCouponList.Skeleton = function Skeleton() {
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
