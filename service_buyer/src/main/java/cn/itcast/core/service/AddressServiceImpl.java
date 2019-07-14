package cn.itcast.core.service;

import cn.itcast.core.dao.address.AddressDao;
import cn.itcast.core.pojo.address.Address;
import cn.itcast.core.pojo.address.AddressQuery;


import java.util.List;

@Service
public class AddressServiceImpl implements cn.itcast.core.service.AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> findAddressListByUserName(String userName) {
        AddressQuery query = new AddressQuery();
        AddressQuery.Criteria criteria = query.createCriteria();
        criteria.andUserIdEqualTo(userName);
        List<Address> addressList = addressDao.selectByExample(query);
        System.out.println("123");
        return addressList;
    }
}
