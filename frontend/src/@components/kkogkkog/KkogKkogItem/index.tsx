import { css } from '@emotion/react';

import Placeholder from '@/@components/@shared/Placeholder';

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
        <div>From. {senderName}</div>
        <div>To. {receiverName}</div>
        <div>
          #{modifier} &nbsp;
          <span
            css={css`
              text-decoration: underline 2px;
            `}
          >
            {couponType}
            &nbsp;꼭꼭
          </span>
        </div>
      </Styled.TextContainer>
      <Styled.ImageContainer backgroundColor={backgroundColor}>
        <img src={thumbnail} alt='쿠폰' />
      </Styled.ImageContainer>
    </Styled.Root>
  );
};

KkogKkogItem.CreateLinkButton = function CreateLinkButton() {
  return (
    <Styled.Root>
      <div
        css={css`
          font-size: 32px;

          text-align: center;
          width: 100%;
        `}
      >
        <div>+</div>
        <div
          css={css`
            font-size: 14px;
          `}
        >
          꼭꼭을 생성해보세요 !
        </div>
      </div>
    </Styled.Root>
  );
};

KkogKkogItem.Skeleton = function Skeleton() {
  return <Placeholder aspectRatio='3/1' />;
};

export default KkogKkogItem;

// 글자 수 제한 - 8글자?
// 미디어 쿼리 적용
