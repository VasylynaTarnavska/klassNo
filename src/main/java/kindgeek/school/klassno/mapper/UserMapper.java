package kindgeek.school.klassno.mapper;

import kindgeek.school.klassno.entity.Student;
import kindgeek.school.klassno.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User fromId(Long id){
        if (id == null) {
            return null;
        }

        User user= new User();
        user.setId(id);
        return user;
    }
}
