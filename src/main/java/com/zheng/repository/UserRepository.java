package com.zheng.repository;

import com.zheng.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhenglian on 2016/9/19.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
