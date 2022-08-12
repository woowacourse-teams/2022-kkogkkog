import { createContext, useRef, useState } from 'react';
import ReactDOM from 'react-dom';

import Icon from '@/@components/@shared/Icon';
import theme from '@/styles/theme';

import * as Styled from './style';

export const ToastMessage = ({ isError, message, onClickToast }: any) => {
  return (
    <Styled.ToastContainer isError={isError}>
      <Styled.ToastMessage>
        <div>{message || '다시 시도해주세요'}</div>
        <Icon
          iconName='close'
          color={isError ? theme.colors.red_800 : theme.colors.green_500}
          size='18'
          onClick={onClickToast}
        />
      </Styled.ToastMessage>
    </Styled.ToastContainer>
  );
};

export const ToastContext = createContext({
  displayMessage: (message: string, isError: boolean) => {},
});

const ToastProvider = (props: React.PropsWithChildren) => {
  const { children } = props;

  const timeout = useRef<NodeJS.Timeout | null>(null);

  const [{ message, isError, isShow }, setMessage] = useState<{
    message: string;
    isError: boolean;
    isShow: boolean | null;
  }>({
    message: '',
    isError: false,
    isShow: null,
  });

  const displayMessage = (currentMessage: string, isError: boolean) => {
    setMessage(prev => ({ ...prev, isError, message: currentMessage, isShow: true }));

    if (!timeout.current) {
      timeout.current = setTimeout(() => {
        setMessage(prev => ({ ...prev, isShow: false }));

        timeout.current = null;
      }, 1500);
    }
  };

  const onClickToast = () => {
    setMessage(prev => ({ ...prev, isShow: false }));

    if (timeout.current) {
      clearTimeout(timeout.current);

      timeout.current = null;
    }
  };

  return (
    <ToastContext.Provider value={{ displayMessage }}>
      {children}
      {ReactDOM.createPortal(
        <>
          {isShow === true && (
            <Styled.ShowUpRoot>
              <ToastMessage message={message} isError={isError} onClickToast={onClickToast} />
            </Styled.ShowUpRoot>
          )}
          {isShow === false && (
            <Styled.ShowDownRoot>
              <ToastMessage message={message} isError={isError} onClickToast={onClickToast} />
            </Styled.ShowDownRoot>
          )}
        </>,
        document.querySelector('#root') as Element
      )}
    </ToastContext.Provider>
  );
};

export default ToastProvider;
