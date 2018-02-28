package domain;

import domain.KwetterUser;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-02-28T10:02:10")
@StaticMetamodel(KwetterUser.class)
public class KwetterUser_ { 

    public static volatile SingularAttribute<KwetterUser, String> website;
    public static volatile ListAttribute<KwetterUser, KwetterUser> following;
    public static volatile SingularAttribute<KwetterUser, String> name;
    public static volatile SingularAttribute<KwetterUser, String> photo;
    public static volatile SingularAttribute<KwetterUser, String> bio;
    public static volatile SingularAttribute<KwetterUser, String> location;
    public static volatile SingularAttribute<KwetterUser, Long> id;
    public static volatile SingularAttribute<KwetterUser, String> username;

}