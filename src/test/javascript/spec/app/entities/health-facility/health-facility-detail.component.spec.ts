/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { HealthFacilityDetailComponent } from '../../../../../../main/webapp/app/entities/health-facility/health-facility-detail.component';
import { HealthFacilityService } from '../../../../../../main/webapp/app/entities/health-facility/health-facility.service';
import { HealthFacility } from '../../../../../../main/webapp/app/entities/health-facility/health-facility.model';

describe('Component Tests', () => {

    describe('HealthFacility Management Detail Component', () => {
        let comp: HealthFacilityDetailComponent;
        let fixture: ComponentFixture<HealthFacilityDetailComponent>;
        let service: HealthFacilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [HealthFacilityDetailComponent],
                providers: [
                    HealthFacilityService
                ]
            })
            .overrideTemplate(HealthFacilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HealthFacilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HealthFacilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HealthFacility(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.healthFacility).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
