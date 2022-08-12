import { p } from 'msw/lib/glossary-58eca5a8';
import { useMemo, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

import Icon from '@/@components/@shared/Icon';
import Position from '@/@components/@shared/Position';
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
    navigate(`/coupon-list/${coupon.id}`);
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

  return (
    <Position>
      <Styled.Root>
        {Object.keys(acceptedCouponList).length === 0 ? (
          <Styled.NoneContentsContainer>
            <Icon iconName='hand' size='36' color={theme.colors.primary_400} />
            <h3>아직 승인된 약속이 존재하지 않아요!</h3>
            <h4>약속을 기다리는 사람에게 신청해볼까요 ?</h4>
          </Styled.NoneContentsContainer>
        ) : (
          sortedKey.map(date => (
            <Styled.DateContainer key={date}>
              <Styled.DateTitle>{generateDateText(date)}</Styled.DateTitle>
              <VerticalCouponList
                CouponItem={BigCouponItem}
                couponList={acceptedCouponList[date]}
                onClickCouponItem={onClickCouponItem}
              />
            </Styled.DateContainer>
          ))
        )}
        {/* <Styled.LeftArrow onClick={onClickLeftArrow}>
          <Icon iconName='arrow' color={theme.colors.primary_400} size='36' />
        </Styled.LeftArrow>
        <Styled.RightArrow onClick={onClickRightArrow}>
          <Icon iconName='arrow' color={theme.colors.primary_400} size='36' />
        </Styled.RightArrow> */}
      </Styled.Root>
    </Position>
  );
};

export default AcceptedCouponList;
