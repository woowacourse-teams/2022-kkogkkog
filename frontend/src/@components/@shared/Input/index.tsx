import { css } from '@emotion/react';
import { InputHTMLAttributes } from 'react';

import theme from '@/styles/theme';

import * as Styled from './style';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label: string;
  additionalLabel?: string;
}

const Input = ({ label, additionalLabel, id, type = 'text', ...props }: InputProps) => {
  return (
    <Styled.Root>
      <label htmlFor={id}>{label}</label>
      {additionalLabel && (
        <div
          css={css`
            font-size: 12px;
            color: ${theme.colors.grey_200};
            margin-bottom: 8px;
          `}
        >
          {additionalLabel}
        </div>
      )}
      <input id={id} type={type} {...props} />
    </Styled.Root>
  );
};

export default Input;
