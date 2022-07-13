import { css } from '@emotion/react';
import { InputHTMLAttributes } from 'react';

import theme from '@/styles/theme';

import * as Styled from './style';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
  additional?: string;
}

const Input = ({ label, additional, id, type = 'text', ...props }: InputProps) => {
  return (
    <Styled.Root>
      <label htmlFor={id}>{label}</label>
      {additional && (
        <div
          css={css`
            font-size: 12px;
            color: ${theme.colors.grey_200};
            margin-bottom: 8px;
          `}
        >
          {additional}
        </div>
      )}
      <input id={id} type={type} {...props} />
    </Styled.Root>
  );
};

export default Input;
