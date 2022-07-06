import * as Styled from './style';

const KkogKkogItem = ({
  id,
  senderName,
  receiverName,
  backgroundColor,
  modifier,
  type,
  thumbnail,
}) => {
  return (
    <Styled.Root>
      <Styled.TextContainer>
        {senderName}이(가) {modifier}
        <br />
        {type} 꼭꼭
      </Styled.TextContainer>
      <Styled.ImageContainer backgroundColor={backgroundColor}>
        <img src={thumbnail} alt='쿠폰' />
      </Styled.ImageContainer>
    </Styled.Root>
  );
};

export default KkogKkogItem;
