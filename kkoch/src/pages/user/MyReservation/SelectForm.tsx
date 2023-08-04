import { useState } from 'react';
import Select, { components, InputActionMeta, StylesConfig } from 'react-select'

interface flowerOption {
  flower: object // 품목
  variety: object // 품종
  price: number // 가격
  quantity: number // 단수
  location: string // 지역
}

type Props = {
  placeholder: string;
}

const customStyles: StylesConfig = {
  control: (base) => ({
    ...base,
    flexDirection: 'row-reverse',
  }),
  clearIndicator: (base) => ({
    ...base,
    position: 'absolute',
    right: 0,
  }),
}

const flowerOption = [
  { value: 'rose', label: 'Rose'},
  { value: 'tulip', label: 'Tulip'},
  { value: 'sumflower', label: 'Sunflower'},
  { value: 'lavender', label: 'Lavender'},
  { value: 'hydrangea', label: 'Hydrangea'},
]

// 실시간 품종, 품목 데이터
const performSearchRequest = async (searchText: string) => {
  const response = await fetch(
    `https://countries-api-for-blog.vercel.app/api/countries${
      searchText ? '/' + searchText : ''
    }`
  )
  return await response.json()
}

export default function SelectForm({placeholder} : Props) {
  const [inputText, setInputText] = useState<string>('')
  const handleInputChange = (inputText: string, meta: InputActionMeta) => {
    if (meta.action !== 'input-blur' && meta.action !== 'menu-close') {
      setInputText(inputText)
      handleSearch(inputText)
    }
  }
  
  const [flower, setFlower] = useState<flowerOption[]>([])
  const [isLoading, setIsLoading] = useState<boolean>(false)
  
  const handleSearch = async (searchQuery: string) => {
    if (searchQuery.trim().length === 0) {
      setFlower([])
      return
    }
  
    setIsLoading(true)
  
    let flowers = []
    try {
      flowers = await performSearchRequest(searchQuery)
    } catch (e) {
      console.error(e)
    } finally {
      setFlower(flowers)
      setIsLoading(false)
    }
  }

  const noOptionsMessage = (obj: { inputValue: string }) => {
    if (obj.inputValue.trim().length === 0) {
      return null
    }
    return 'No matching countries'
  }

  return (
    <>
      <Select 
        placeholder={placeholder}
        options={flower} 
        isClearable={true} 
        isSearchable={true} 
        components={{
          IndicatorSeparator: () => null,
          DropdownIndicator,
        }}
        styles={customStyles}
        inputValue={inputText}
        onInputChange={handleInputChange}
        isLoading={isLoading}
        filterOption={null}
        noOptionsMessage={noOptionsMessage}
      />
    </>
  );
}


const DropdownIndicator = (props) => {
  return (
    components.DropdownIndicator && (
      <components.DropdownIndicator {...props}>
        <SearchIcon />
      </components.DropdownIndicator>
    )
  )
}

const SearchIcon = () => (
  <svg
    width="22"
    height="22"
    viewBox="0 0 100 100"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
  >
    <circle cx="38" cy="40" r="20.5" stroke="currentColor" strokeWidth="7" />
    <path
      d="M76.0872 84.4699C78.056 86.4061 81.2217 86.3797 83.158 84.4109C85.0943 82.442 85.0679 79.2763 83.099 77.34L76.0872 84.4699ZM50.4199 59.2273L76.0872 84.4699L83.099 77.34L57.4317 52.0974L50.4199 59.2273Z"
      fill="currentColor"
    />
  </svg>
)