import { css } from '@emotion/react';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import SelectInput from '@/@components/@shared/SelectInput';
import { colors, couponTypes, modifiers } from '@/@pages/kkogkkog-list/create';

import * as Styled from './style';

const KkogKkogCreateForm = ({
  currentSenderName,
  onChangeSenderName,
  currentReceiverName,
  onChangeReceiverName,
  currentType,
  onSelectType,
  currentModifier,
  onSelectModifier,
  currentColor,
  onSelectColor,
  currentMessage,
  onChangeMessage,
  onSubmitCreateForm,
}) => {
  return (
    <Styled.FormRoot onSubmit={onSubmitCreateForm}>
      <Input
        label='누가 주는건가요?'
        placeholder='보여질 닉네임을 자유롭게 적어주세요!'
        value={currentSenderName}
        onChange={onChangeSenderName}
      />

      <Input
        label='누구에게 주고 싶나요?'
        placeholder='상대방의 닉네임을 입력해주세요!'
        value={currentReceiverName}
        onChange={onChangeReceiverName}
      />

      <SelectInput label='어떤 쿠폰인가요?'>
        {couponTypes.map(({ type, imageURL }) => (
          <Styled.TypeOption
            key={type}
            onClick={() => onSelectType(type)}
            isSelected={type === currentType}
          >
            <img src={imageURL} alt='hi' />
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput label='당신의 기분을 골라주세요'>
        {modifiers.map(modifier => (
          <Styled.FeelOption
            key={modifier}
            onClick={() => onSelectModifier(modifier)}
            isSelected={modifier === currentModifier}
          >
            #{modifier}
          </Styled.FeelOption>
        ))}
      </SelectInput>

      <SelectInput label='쿠폰의 색상을 골라주세요'>
        {colors.map(color => (
          <Styled.ColorOption
            key={color}
            color={color}
            onClick={() => onSelectColor(color)}
            isSelected={color === currentColor}
          />
        ))}
      </SelectInput>

      <Input
        label='하고 싶은 말을 적어주세요'
        placeholder='쿠폰을 사용하는 사람을 생각하며 적어주세요!'
        value={currentMessage}
        onChange={onChangeMessage}
      />

      <Styled.ButtonContainer>
        <div
          css={css`
            width: 30%;
          `}
        >
          <Button>꼭꼭 발급하기</Button>
        </div>
      </Styled.ButtonContainer>
    </Styled.FormRoot>
  );
};

export default KkogKkogCreateForm;
