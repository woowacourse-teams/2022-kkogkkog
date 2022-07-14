import { PropsWithChildren } from 'react';

import * as Styled from './style';

interface DimmedProps {
  position?: 'top' | 'middle' | 'bottom';
  onCloseModal?: () => void;
}

const Dimmed = (props: PropsWithChildren<DimmedProps>) => {
  const { position, onCloseModal, children } = props;

  return (
    <Styled.Root
      position={position}
      onClick={e => {
        if (e.target !== e.currentTarget) return;
        onCloseModal();
      }}
    >
      {children}
    </Styled.Root>
  );
};

export default Dimmed;
