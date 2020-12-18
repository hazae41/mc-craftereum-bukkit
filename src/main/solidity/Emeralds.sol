// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.8.0;

import "./Craftereum.sol";
import "./IERC20.sol";

abstract contract IEmeralds is IERC20 {
    Craftereum public craftereum;

    function balance() external virtual view returns (uint);

    function mint(address account, uint amount) external virtual returns (bool);

    function burn(address account, uint amount) external virtual returns (bool);
}