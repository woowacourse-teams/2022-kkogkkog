import { ChangeEventHandler, Dispatch, SetStateAction, useState } from 'react';

const useInput = (): [
  string,
  Dispatch<SetStateAction<string>>,
  ChangeEventHandler<HTMLInputElement>
] => {
  const [value, setValue] = useState('');

  const onChange: ChangeEventHandler<HTMLInputElement> = e => {
    const { target } = e;

    setValue(target.value);
  };

  return [value, setValue, onChange];
};

export default useInput;
