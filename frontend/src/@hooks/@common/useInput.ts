import { ChangeEventHandler, Dispatch, SetStateAction, useState } from 'react';

type Validation = (value: string) => boolean;

const useInput = <T extends string>(
  defaultValue: T,
  validations?: Validation[]
): [T, ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement>, Dispatch<SetStateAction<T>>] => {
  const [value, setValue] = useState(defaultValue);

  const onChange: ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = e => {
    const { target } = e;

    const value = target.value as T;

    // @TODO: validation에 에러 메시지 추가
    if (validations?.some(validation => validation(value))) {
      return;
    }
    setValue(value);
  };

  return [value, onChange, setValue];
};

export default useInput;
