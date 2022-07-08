import styled from '@emotion/styled';

export const Root = styled.div`
  width: 100%;
  height: 54px;

  position: sticky;
  top: 0;

  background-color: ${({ theme }) => theme.colors.white_100};

  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 0 10px;

  color: ${({ theme }) => theme.colors.primary_400};
  font-weight: bold;

  border-radius: 4px 4px 0 0;

  box-shadow: 0 4px 4px 0 #00000025;

  z-index: 99999;
`;

export const Logo = styled.div``;

export const Title = styled.div`
  font-size: 14px;
`;

export const Profile = styled.div``;
