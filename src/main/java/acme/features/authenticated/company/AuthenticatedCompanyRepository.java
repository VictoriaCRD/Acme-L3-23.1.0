
package acme.features.authenticated.company;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id = :userAccountId")
	UserAccount findOneUserAccountById(int userAccountId);

	@Query("select c from Company c where c.userAccount.id = :CompanyId")
	Company findOneCompanyById(int CompanyId);

}
