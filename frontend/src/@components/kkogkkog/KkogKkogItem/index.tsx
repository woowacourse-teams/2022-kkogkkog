import { css } from '@emotion/react';

import * as Styled from './style';

const mediaQuery = css`
  @media (max-width: 400px) {
    font-size: 16px;
  }

  @media (max-width: 360px) {
    font-size: 14px;
  }
`;

const KkogKkogItem = ({
  id,
  senderName,
  receiverName,
  backgroundColor,
  modifier,
  couponType,
  thumbnail,
}) => {
  return (
    <Styled.Root>
      <Styled.TextContainer css={mediaQuery}>
        From. {senderName}
        <br />#{modifier}
        &nbsp;
        <span
          css={css`
            text-decoration: underline 2px;
          `}
        >
          {couponType}
        </span>
        &nbsp;꼭꼭
      </Styled.TextContainer>
      <Styled.ImageContainer backgroundColor={backgroundColor}>
        <img src={thumbnail} alt='쿠폰' />
      </Styled.ImageContainer>
    </Styled.Root>
  );
};

export default KkogKkogItem;

// 글자 수 제한 - 8글자?
// 미디어 쿼리 적용
